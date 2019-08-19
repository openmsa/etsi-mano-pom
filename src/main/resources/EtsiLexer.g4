lexer grammar EtsiLexer;

@lexer::header {
	package com.ubiqube.etsi.mano.grammar;
}

COMMA: ',';
SLASH: '/';
OPEN_BRACKET: '(';
CLOSE_BRACKET: ')';
SEMICOLON: ';';
EQUAL: '=' -> mode(VALUES);
DOT: '.';
AMPERSAND: '&';

EQ: 'eq';
NEQ: 'neq';

GT: 'gt';
LT: 'lt';
GTE: 'gte';
LTE: 'lte';

IN: 'in';
NIN: 'nin';
CONT: 'cont';
NCONT: 'ncont'; 
FILTER: 'filter';

ATTRIBUTE: [a-zA-Z0-9]+;

mode VALUES;

STRING: ~[.=,&]+ -> mode(DEFAULT_MODE);


