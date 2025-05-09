package com.chengfu.shopping.pojo;


import lombok.Data;

/**
 * @author Cheng Fu
 * @description: 用户类，使用lomhok注解实现get和set方法
 * @date 2025/5/9 10:24
 */
@Data
public class User {
	private String UserName;
	private String UserPassword;
}
