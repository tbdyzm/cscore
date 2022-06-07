#include<stdio.h>

int main(void){
    char c = 'c';
    unsigned uc = 'c';
    signed char sc = 'c';

    int i = -1;
    unsigned int ui = -1;

    short s = -1;
    unsigned short us = 1;

    long l = -1;
    unsigned long ul = -1;

    float f = 0.01;
    double d = -0.01;
    long double ld = -0.01;

    char str[] = "hello";

    for(int idx = 0; idx < 4; idx++){
    	if(idx%3 == 0){
    	    printf("▁markedStart%%a▁markedEnd\n");
    	    printf("▁markedStart%%c▁markedEnd, ▁markedStart%%c▁markedEnd, ▁markedStart%%c▁markedEnd\n");
    	    printf("▁markedStart%%d▁markedEnd, ▁markedStart%%d▁markedEnd, ▁markedStart%%d▁markedEnd, ▁markedStart%%d▁markedEnd\n");
    	    printf("▁markedStart%%e▁markedEnd\n");
    	    printf("▁markedStart%%f▁markedEnd, ▁markedStart%%f▁markedEnd\n");
    	}
    	else if(idx%3 == 1){
    	    printf("▁markedStart%%g▁markedEnd\n");
    	    printf("▁markedStart%%i▁markedEnd\n");
    	    printf("▁markedStart%%o▁markedEnd\n");
    	    printf("▁markedStart%%p▁markedEnd\n");
    	    printf("▁markedStart%%s▁markedEnd\n");
    	    printf("▁markedStart%%u▁markedEnd\n");
    	}
    	else{
    	    printf("▁markedStart%%x▁markedEnd\n");
    	    printf("▁markedStart%%A▁markedEnd\n");
    	    printf("▁markedStart%%E▁markedEnd\n");
    	    printf("▁markedStart%%G▁markedEnd\n");
    	    printf("▁markedStart%%X▁markedEnd\n");
    	}
    }

    printf("%% no format specifier %%\n");
    printf("%% format specifier ▁markedStart%%-5.4f▁markedEnd\n");
    printf("%% no format specifier %%\n");printf("%% no format specifier %%\n");printf("%% no format specifier %%\n");
    printf("%% multi format specifier in one line ▁markedStart%%-5.4f▁markedEnd\n");printf("▁markedStart%%d▁markedEnd, ▁markedStart%%d▁markedEnd, ▁markedStart%%d▁markedEnd, ▁markedStart%%d▁markedEnd\n");printf("▁markedStart%% #010.4le▁markedEnd, ▁markedStart%%-+#10.4Le▁markedEnd\n");
    printf("%% no format specifier %%\n");printf("%% format specifier ▁markedStart%%-5.4f▁markedEnd\n");printf("▁markedStart%%d▁markedEnd, ▁markedStart%%d▁markedEnd, ▁markedStart%%d▁markedEnd, ▁markedStart%%d▁markedEnd\n");

    printf("▁markedStart%% 8jd▁markedEnd, ▁markedStart%% 08jd▁markedEnd\n");

    return 0;
}
