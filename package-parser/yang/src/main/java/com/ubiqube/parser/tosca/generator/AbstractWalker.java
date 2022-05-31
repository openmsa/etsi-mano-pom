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

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;

public abstract class AbstractWalker implements YangListener {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractWalker.class);
	private final LinkedList<Context> stack = new LinkedList<>();
	protected JDefinedClass currentClass = null;
	protected JFieldVar currentField;
	protected String currentClassName;

	@Override
	public void startClass(final String className, final String comment) {
		if (null != currentClass) {
			final Context ctx = new Context(currentClass, currentField, currentClassName);
			stack.push(ctx);
			LOG.debug("Pushing context => {}", currentClassName);
		}
		onClassStart(className, comment);
	}

	protected abstract void onClassStart(String className, String comment);

	@Override
	public void terminateClass() {
		onClassTerminate();
		if (!stack.isEmpty()) {
			final Context context = stack.pop();
			LOG.debug("Poping context => {} restoring => {}", currentClassName, context.currentClassName);
			currentClass = context.currentClass;
			currentField = context.currentField;
			currentClassName = context.currentClassName;
		}
	}

	protected abstract void onClassTerminate();

	class Context {
		protected JDefinedClass currentClass = null;
		protected JFieldVar currentField;
		protected String currentClassName;

		public Context(final JDefinedClass _currentClass, final JFieldVar _currentField, final String _currentClassName) {
			currentClass = _currentClass;
			currentField = _currentField;
			currentClassName = _currentClassName;
		}
	}
}
