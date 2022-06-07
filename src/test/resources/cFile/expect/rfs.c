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
    	    printf("▁replaced[0]\n");
    	    printf("▁replaced[1], ▁replaced[2], ▁replaced[3]\n");
    	    printf("▁replaced[4], ▁replaced[5], ▁replaced[6], ▁replaced[7]\n");
    	    printf("▁replaced[8]\n");
    	    printf("▁replaced[9], ▁replaced[10]\n");
    	}
    	else if(idx%3 == 1){
    	    printf("▁replaced[11]\n");
    	    printf("▁replaced[12]\n");
    	    printf("▁replaced[13]\n");
    	    printf("▁replaced[14]\n");
    	    printf("▁replaced[15]\n");
    	    printf("▁replaced[16]\n");
    	}
    	else{
    	    printf("▁replaced[17]\n");
    	    printf("▁replaced[18]\n");
    	    printf("▁replaced[19]\n");
    	    printf("▁replaced[20]\n");
    	    printf("▁replaced[21]\n");
    	}
    }

    printf("%% no format specifier %%\n");
    printf("%% format specifier ▁replaced[22]\n");
    printf("%% no format specifier %%\n");printf("%% no format specifier %%\n");printf("%% no format specifier %%\n");
    printf("%% multi format specifier in one line ▁replaced[23]\n");printf("▁replaced[24], ▁replaced[25], ▁replaced[26], ▁replaced[27]\n");printf("▁replaced[28], ▁replaced[29]\n");
    printf("%% no format specifier %%\n");printf("%% format specifier ▁replaced[30]\n");printf("▁replaced[31], ▁replaced[32], ▁replaced[33], ▁replaced[34]\n");

    printf("▁replaced[35], ▁replaced[36]\n");

    return 0;
}
