parser grammar Etsifilter;

options { tokenVocab=EtsiLexer; }

@header {
	package com.ubiqube.etsi.grammar;
}
filterExpr 
		: simpleFilterExpr(AMP simpleFilterExpr)*
		;

simpleFilterExpr
		: attrName(DOT attrName)*(DOT op) EQUAL value (COMA value)*
		;

op
		: EQ
		| NEQ 
		| GT 
		| LT 
		| GTE 
		| LTE 
		| CONT 
		| NCONT
		;



attrName
		: STRING
		;
value
		: VALUE
		;
