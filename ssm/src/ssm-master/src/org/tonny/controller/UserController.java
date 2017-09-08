package org.tonny.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.apache.log4j.Logger;  
import org.tonny.model.User;
import org.tonny.service.IUserService;

@Controller
public class UserController
{
    private static final Logger log = Logger.getLogger(UserController.class);  
	@Autowired
	private IUserService userService;
	
    @RequestMapping(value = "/doLogin")
    //public String doLogin(HttpServletRequest request)
	public String doLogin(@RequestParam("username") String userName, @RequestParam("password") String pwd, Map<String,Object> map)
	{
        boolean authorized = userService.isAuthorizedUser(userName, pwd);
        if(authorized)
        {
        	List<User> userList = userService.getAll();
    		map.put("userList", userList);
            return "user/user_list";
        }
        else
        {
        	return "login";
        }
    }
	
	@RequestMapping(value = "/getall")
	public String getAll(Map<String,Object> map)
	{
		List<User> userList = userService.getAll();
		map.put("userList", userList);
		return "user/user_list";
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public ModelAndView query(@RequestParam("username")String userName)
	{
		User user = new User();
		user.setName(userName);
		
		List<User> userList = userService.getUserListByCondition(user);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("userList", userList);
		modelAndView.setViewName("user/user_list");
		return modelAndView;
	}
	
	@RequestMapping(value = "/toadd")
    public String toAdd()
    {
        return "user/user_add";
    }
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(User user)
    {
		log.info(user);
	    userService.add(user);
        return "redirect:/getall";
    }
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable(value="id") Integer id, Map<String,Object> map)
	{
		User user = userService.getUserById(id);
		map.put("user", user);
		return "user/user_edit";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(User user)
    {
        userService.update(user);
        return "redirect:/getall";
    }
	
	@RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value="id") Integer id)
    {
        userService.deleteById(id);
        return "redirect:/getall";
    }
	
	@RequestMapping("/toUploadPage")
	public String toUploadPage()
	{
		return "upload";
	}
	
	
	@RequestMapping("/")
	public String getIndex()
	{
		return "login";
	}

	/**
	 * desc:上传文件
	 * @param multifiles
	 * @param request
	 * @return
	 * @throws Exception
	 * date:2017年1月24日
	 * author:Tonny Chien
	 */
	@RequestMapping("/upload")
	public String upload(@RequestParam MultipartFile[] multifiles, HttpServletRequest request) throws Exception
	{
		for (MultipartFile file : multifiles)
		{
			// 此处MultipartFile[]表明是多文件,如果是单文件MultipartFile就行了
			if (file.isEmpty())
			{
				log.info("文件未上传!");
			}
			else
			{
				// 得到上传的文件名
				String fileName = file.getOriginalFilename();
				// 得到服务器项目发布运行所在地址
				String imgPath = "F:" + File.separator;
				// 此处未使用UUID来生成唯一标识,用日期做为标识
				String path = imgPath + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + fileName;
				//String path = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + fileName;
				// 查看文件上传路径,方便查找
				log.info(path);
				// 把文件上传至path的路径
				File localFile = new File(path);
				file.transferTo(localFile);
			}
		}
		return "success";
	}
	
	/**
	 * desc:下载文件
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 * date:2017年1月24日
	 * author:Tonny Chien
	 */
	@RequestMapping("/download")
	public String download(String fileName, HttpServletRequest request, HttpServletResponse response)
	{
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try
		{
			//String path = request.getSession().getServletContext().getRealPath("image") + File.separator;
			String path = "F:"+ File.separator;
			InputStream inputStream = new FileInputStream(new File(path + fileName));

			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0)
			{
				os.write(b, 0, length);
			}

			os.flush();
			// 这里主要关闭。
			os.close();

			inputStream.close();
		}
		catch (Exception e)
		{
			log.error(e);
		}
		// 返回值要注意，要不然就出现下面这句错误！
		// java+getOutputStream() has already been called for this response
		return null;
	}
	
	/**
	 * desc:json请求操作示例， method的方式需要与json的请求方式对应起来
	 * @param id
	 * @param type
	 * @return
	 * date:2017年2月3日
	 * author:Tonny Chien
	 */
	@RequestMapping(value = "/jsonOper", method = RequestMethod.POST)
	public @ResponseBody String jsonSource(@RequestParam("id")Integer id, @RequestParam("type")String type)
	{
		System.out.println("id: " + id + "type: " + type);
		return "OK";

	}
}
