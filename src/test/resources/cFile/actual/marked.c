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
    	    printf("▁markedStart%a▁markedEnd\n", d);
    	    printf("▁markedStart%c▁markedEnd, ▁markedStart%c▁markedEnd, ▁markedStart%c▁markedEnd\n", c, uc, sc);
    	    printf("▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd\n", i, ui, s, us);
    	    printf("▁markedStart%e▁markedEnd\n", d);
    	    printf("▁markedStart%f▁markedEnd, ▁markedStart%f▁markedEnd\n", f, d);
    	}
    	else if(idx%3 == 1){
    	    printf("▁markedStart%g▁markedEnd\n", f);
    	    printf("▁markedStart%i▁markedEnd\n", i);
    	    printf("▁markedStart%o▁markedEnd\n", ui);
    	    printf("▁markedStart%p▁markedEnd\n", str);
    	    printf("▁markedStart%s▁markedEnd\n", str);
    	    printf("▁markedStart%u▁markedEnd\n", ui);
    	}
    	else{
    	    printf("▁markedStart%x▁markedEnd\n", i);
    	    printf("▁markedStart%A▁markedEnd\n", d);
    	    printf("▁markedStart%E▁markedEnd\n", d);
    	    printf("▁markedStart%G▁markedEnd\n", f);
    	    printf("▁markedStart%X▁markedEnd\n", i);
    	}
    }

    printf("%% no format specifier %%\n");
    printf("%% format specifier ▁markedStart%-5.4f▁markedEnd\n", f);
    printf("%% no format specifier %%\n");printf("%% no format specifier %%\n");printf("%% no format specifier %%\n");
    printf("%% multi format specifier in one line ▁markedStart%-5.4f▁markedEnd\n", f);printf("▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd\n", i, ui, s, us);printf("▁markedStart% #010.4le▁markedEnd, ▁markedStart%-+#10.4Le▁markedEnd\n", d, ld);
    printf("%% no format specifier %%\n");printf("%% format specifier ▁markedStart%-5.4f▁markedEnd\n", f);printf("▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd\n", i, ui, s, us);

    printf("▁markedStart% 8jd▁markedEnd, ▁markedStart% 08jd▁markedEnd\n", l, ul);

    return 0;
}
