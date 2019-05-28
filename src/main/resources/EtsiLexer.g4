lexer grammar EtsiLexer;
@header {
	package com.ubiqube.etsi.grammar;
}

EQ:			'eq';
NEQ:		'neq';
GT:			'gt';
LT:			'lt';
GTE:		'gte';
LTE:		'lte';
CONT:		'cont';
NCONT:		'ncont';

DOT:		'.';
AMP:		'&';
EQUAL:		'=';
COMA:		',';

STRING:		('a'..'z'|'A'..'Z')+;
VALUE:		('a'..'z'|'A'..'Z'|'0'..'9')+;
