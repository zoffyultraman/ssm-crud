package com.user.mapper;
import java.util.*;

import com.user.POJO.POJO;
import com.user.POJO.POJO.Category;

public interface userMapper {
	  
    public void add(Category category);  
        
    public void delete(int id);  
        
    public Category get(int id);  
      
    public void update(Category category);   
        
    public List<Category> list();
     
    public int count();  
     
}