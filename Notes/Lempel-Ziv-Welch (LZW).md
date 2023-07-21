#  Lempel-Ziv-Welch (LZW)

The Lemepl-Ziv Welch algorithm is one of many algorithms used for compression. It is typically used to compress certain image files, unix's 'compress' command, among other uses. It is lossless, meaning no data is lost when compressing.

The idea: rely on reoccurring patterns to save data space.

* Example: ASCII code

  * Typically, every character is stored with 8 binary bits, allowing up to 256 unique symbols for the idea.

  * This algorithm tries to extend this library to 9 to 12 bits per character. The new unique symbols are made up of combinations of symbols that occurred previously in the string.

  * Does not always compress well, especially with short, diverse strings. But is good for compressing redundant data, and does not have to save the new dictionary with the data: this method can both compress and uncompress data.

    

Compressed String = 65 66 67 256 258 257 259


------
Dictionary
0 = NUL         <span style= "color:blue">Static Section</span>
\---
65 = A 
66 = B 			<span style= "color:blue">ASCII Values</span>
67 = C

\---
259 = DEL 
\-------------------
256 = <span style="color:red">AB</span>
257 = <span style="color:red">BC</span>            <span style= "color:blue"> Dynamic Section </span>
258 = <span style="color:red">CA</span>

259 = <span style="color:red">ABC</span>

260 = <span style="color:red">CAB</span>			 <span style = "color:blue">Populated with Agorithm</span>
261 = 
262 =
\---
511 =

---------


| $w$  <br />start | Read $k$ | Entry | Ouput | Dictionary Add | Next<br />$w$ |
| ---------------- | -------- | ----- | ----- | -------------- | ------------- |
| Nil              | 65       | A     | A     |                | A             |
| A                | 66       | B     | B     | AB 256         | B             |
| B                | 67       | C     | C     | BC 257         | C             |
| C                | 256      | AB    | AB    | CA 258         | AB            |
| AB               | 258      | CA    | CA    | ABC 259        | CA            |
| CA               | 257      | BC    | BC    | CAB 260        | BC            |
| BC               | 259      | ABC   | ABC   | BCA 261        | ABC           |
|                  |          |       |       |                |               |

Output: A, B, C, AB, CA, BC, ABC

Everything is in the dictionary. There is only one exception to this and the rules for creating the entry are very straight forward and clear.



```pseudocode
read a character k
entry = dictionary entry for k
output entry
w = entry
loop
	read a character k
	entry = dictionary entry for k
	output entry
	add w + first char of entry to the dictionary
	w = entry
endloop

```





```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define NOT_EXIST -1

typedef struct {
	char** seq; // char array 
	int* code; // 数字的数组
        int size; // 编码到哪个
	int max_size; // 字典一共最大多少	
} Dictionary; // 设计字典的数据结构

void insert_seq(Dictionary* dict, char* seq){
	int i = dict->size;
	dict->seq[i] = malloc(sizeof(char) * strlen(seq)+1);
	dict->code[i] = i;
	dict->size++;
	strcpy(dict->seq[i], seq);

}
void init_dictionary(Dictionary* dict, int max_size){ // 初始化字典
	dict->max_size = max_size;
	dict->size = 0;
	dict->seq = malloc(sizeof(char*) * max_size); //新建maxsize的大小的数组
	dict->code = malloc(sizeof(int) * max_size);

	insert_seq(dict, "#");
	char seq[2] = "A";
	for (int i = 0; i < 26; i++){
		insert_seq(dict, seq);
		seq[0]++;
	}

}

int get_seq_code(Dictionary* dict, char* seq){
	for (int i = 0; i < dict->size; i++){
		if (strcmp(dict->seq[i], seq) == 0){
			return dict->code[i];
		}
	}
	return NOT_EXIST;
}


void print_dictionary(Dictionary* dict){
	printf("=================\n");
	printf("Code      Sequence\n");
	printf("=================\n");
	for (int i = 0; i <dict->size; i++){
		printf("%5d%7c", dict->code[i], ' ');
		printf("%s\n", dict->seq[i]);
	}

	printf("==================\n");
}

void lzw_encode(char* text, Dictionary* dict){
	char current[1000];
	char next;
	int code;
	int i = 0;
	while (i < strlen(text)){
		sprintf(current, "%c", text[i]);
		next = text[i+1];
		while (get_seq_code(dict, current) != NOT_EXIST){
			sprintf(current, "%s%c", current, next);
			i++;
			next = text[i+1];
		}
		current[strlen(current) - 1] = '\0';
		next = text[i];
		code = get_seq_code(dict, current);
		sprintf(current, "%s%c", current, next);
		insert_seq(dict, current);

		printf("%d %s\n", code, current);

	}
}

int main(){
	Dictionary dict;
	init_dictionary(&dict, 1000);
	print_dictionary(&dict);

	lzw_encode("TOBEORNOTTOBEORTOBEORNOT", &dict);

	// printf("%d\n", get_seq_code(&dict, "B"));
	return 0;
}
```

Result

```c
eve@Eves-Air Notes % vim encoderLZW.c
eve@Eves-Air Notes % gcc encoderLZW.c
eve@Eves-Air Notes % ./a.out         
=================
Code      Sequence
=================
    0       #
    1       A
    2       B
    3       C
    4       D
    5       E
    6       F
    7       G
    8       H
    9       I
   10       J
   11       K
   12       L
   13       M
   14       N
   15       O
   16       P
   17       Q
   18       R
   19       S
   20       T
   21       U
   22       V
   23       W
   24       X
   25       Y
   26       Z
==================
20 TO
15 OB
2 BE
5 EO
15 OR
18 RN
14 NO
15 OT
20 TT
27 TOB
29 BEO
31 ORT
36 TOBE
30 EOR
32 RNO
34 OT
eve@Eves-Air Notes % 
```

Reference:

https://www.bilibili.com/video/BV1ds411W7sX/?spm_id_from=333.999.0.0&vd_source=73e7d2c4251a7c9000b22d21b70f5635

https://mattmahoney.net/dc/dce.html#Section_53