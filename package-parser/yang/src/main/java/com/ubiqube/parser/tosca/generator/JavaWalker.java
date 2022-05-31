/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubiqube.parser.tosca.generator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.opendaylight.mdsal.binding.model.api.Enumeration;
import org.opendaylight.mdsal.binding.model.api.Type;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JEnumConstant;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JVar;
import com.ubiqube.parser.tosca.ParseException;

public class JavaWalker extends AbstractWalker {
	private final JCodeModel codeModel = new JCodeModel();
	private final Map<String, JDefinedClass> cache = new HashMap<>();
	private final Map<String, JPackage> cachePackage = new HashMap<>();
	private final String directoryOutput;

	public JavaWalker(final String directoryOutput) {
		this.directoryOutput = directoryOutput;
	}

	@Override
	public void startDocument() {
		//
	}

	@Override
	public void onDataTypeExtend(final String derivedFrom) {
		// TODO Auto-generated method stub
	}

	@Override
	public void startEnum(final String className) {
		currentClassName = className;
		final JPackage pack = getPackage(className);
		if (null == pack) {
			throw new ParseException("Unable to add a file without a package.");
		}
		currentClass = createEnum(pack, ClassUtils.getClassName(className));
	}

	@Override
	public void addEnum(final List<Enumeration> enumerations) {
		enumerations.forEach(x -> {
			final JEnumConstant en = currentClass.enumConstant(x.getName());
			en.arg(JExpr.lit(x.getName()));
		});

	}

	@Override
	public void endEnum() {
		cache.put(currentClassName, currentClass);
		currentClass = null;
	}

	@Override
	protected void onClassStart(final String className, final String comment) {
		currentClassName = className;
		final JPackage pack = getPackage(className);
		if (null == pack) {
			throw new ParseException("Unable to add a file without a package.");
		}
		currentClass = createClass(pack, ClassUtils.getClassName(className));
	}

	@Override
	protected void onClassTerminate() {
		cache.put(currentClassName, currentClass);
		currentClass = null;
	}

	private static JDefinedClass createClass(final JPackage pack, final String classname) {
		try {
			return pack._class(classname);
		} catch (final JClassAlreadyExistsException e) {
			throw new ParseException(e);
		}
	}

	private JPackage getPackage(final String key) {
		final String p = ClassUtils.getPackage(key);
		JPackage pack = cachePackage.get(p);
		if (null != pack) {
			return pack;
		}
		pack = codeModel._package(p);
		cachePackage.put(p, pack);
		return pack;
	}

	private static JDefinedClass createEnum(final JPackage pack, final String className) {
		try {
			return pack._enum(className);
		} catch (final JClassAlreadyExistsException e) {
			throw new ParseException(e);
		}
	}

	@Override
	public void startField(final String name, final Type returnType) {
		System.out.println("ret: " + returnType.getName());
		currentField = currentClass.field(JMod.PRIVATE, codeModel.ref(returnType.getFullyQualifiedName()), name);
		currentField.annotate(Valid.class);
	}

	@Override
	public void onFieldTerminate() {
		final String fieldName = currentField.name();
		final String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		final JMethod getter = currentClass.method(JMod.PUBLIC, currentField.type(), "get" + methodName);
		getter.body()._return(currentField);
		// Setter
		final JMethod setVar = currentClass.method(JMod.PUBLIC, codeModel.VOID, "set" + methodName);
		final JVar param = setVar.param(currentField.type(), currentField.name());
		setVar.body().assign(JExpr._this().ref(currentField.name()), JExpr.ref(currentField.name()));
		currentField = null;
	}

	@Override
	public void terminateDocument() {
		try {
			final File file = new File(directoryOutput);
			file.mkdirs();
			codeModel.build(new File(directoryOutput));
		} catch (final IOException e) {
			throw new ParseException(e);
		}
	}

}
