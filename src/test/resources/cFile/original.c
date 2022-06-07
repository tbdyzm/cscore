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
    	    printf("%a\n", d);
    	    printf("%c, %c, %c\n", c, uc, sc);
    	    printf("%d, %d, %d, %d\n", i, ui, s, us);
    	    printf("%e\n", d);
    	    printf("%f, %f\n", f, d);
    	}
    	else if(idx%3 == 1){
    	    printf("%g\n", f);
    	    printf("%i\n", i);
    	    printf("%o\n", ui);
    	    printf("%p\n", str);
    	    printf("%s\n", str);
    	    printf("%u\n", ui);
    	}
    	else{
    	    printf("%x\n", i);
    	    printf("%A\n", d);
    	    printf("%E\n", d);
    	    printf("%G\n", f);
    	    printf("%X\n", i);
    	}
    }

    printf("%% no format specifier %%\n");
    printf("%% format specifier %-5.4f\n", f);
    printf("%% no format specifier %%\n");printf("%% no format specifier %%\n");printf("%% no format specifier %%\n");
    printf("%% multi format specifier in one line %-5.4f\n", f);printf("%d, %d, %d, %d\n", i, ui, s, us);printf("% #010.4le, %-+#10.4Le\n", d, ld);
    printf("%% no format specifier %%\n");printf("%% format specifier %-5.4f\n", f);printf("%d, %d, %d, %d\n", i, ui, s, us);

    printf("% 8jd, % 08jd\n", l, ul);

    return 0;
}
