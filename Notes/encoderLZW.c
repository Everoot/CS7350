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
