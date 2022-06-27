package com.ubiqube.etsi.mano.grammar.antlr;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

import com.ubiqube.etsi.mano.grammar.GrammarException;
import com.ubiqube.etsi.mano.grammar.Node;

public abstract class AbstractAntlrGrammar<T extends ParseTreeListener> {

	public final List<Node<String>> parse(final String query) {
		List<Node<String>> nodes = new ArrayList<>();
		final T treeBuilder = createTreeBuilder();
		if ((null != query) && !query.isEmpty()) {
			final Lexer el = createLexer(query);
			final CommonTokenStream tokens = new CommonTokenStream(el);
			createParser(tokens, treeBuilder);
			nodes = getNodes(treeBuilder);
			checkNodes(nodes);
		}
		return nodes;
	}

	protected abstract Parser createParser(CommonTokenStream tokens, ParseTreeListener treeBuilder);

	protected abstract T createTreeBuilder();

	protected abstract List<Node<String>> getNodes(T treeBuilder);

	protected abstract Lexer createLexer(String query);

	private static void checkNodes(final List<Node<String>> nodes) {
		nodes.forEach(x -> {
			if (null == x.getOp()) {
				throw new GrammarException("Bad filter: " + x);
			}
		});
	}

}
