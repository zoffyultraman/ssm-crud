package com.user.lmpl;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.POJO.POJO.Category;
import com.user.mapper.*;
import com.user.service.*;

@Service
public class userServicelmpl  implements userService{
    @Autowired
    userMapper categoryMapper;
     
    public List<Category> list(){
        return categoryMapper.list();
    };
 
}