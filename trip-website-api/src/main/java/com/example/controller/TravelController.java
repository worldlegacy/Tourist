package com.example.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ann.CurrentUser;
import com.example.ann.RequireLogin;
import com.example.entity.Travel;
import com.example.entity.TravelContent;
import com.example.entity.Userinfo;
import com.example.mongo.domain.TravelComment;
import com.example.mongo.service.ITravelCommentService;
import com.example.qo.TravelQuery;
import com.example.service.ITravelContentService;
import com.example.service.ITravelService;
import com.example.service.IUserinfoService;
import com.example.util.JsonResult;
import com.example.util.UMEditorUploader;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
@RestController
@RequestMapping("/travels")
public class TravelController {
    @Autowired
    private ITravelService travelService;
    @Autowired
    private ITravelContentService travelContentService;
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private ITravelCommentService commentService;

    @RequireLogin
    @GetMapping("/info")
    public JsonResult info(){
        return JsonResult.success();
    }

    //游记分页
    @GetMapping("/query")
    public JsonResult query(TravelQuery qo){
        Page<Travel> page = travelService.queryPage(qo);
        return JsonResult.success(page);
    }

    //百度副文本编辑器上传图片
    @PostMapping("/contentImage")
    public String  contentImage(MultipartFile upfile, HttpServletRequest request) throws Exception {
        UMEditorUploader up = new UMEditorUploader(request);
        String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
        up.setAllowFiles(fileType);
        up.setMaxSize(10000); //单位KB
        up.upload(upfile);

        String callback = request.getParameter("callback");

        String result = "{\"name\":\""+ up.getFileName() +"\", \"originalName\": \""+ up.getOriginalName() +"\", \"size\": "+ up.getSize()
                +", \"state\": \""+ up.getState() +"\", \"type\": \""+ up.getType() +"\", \"url\": \""+ up.getUrl() +"\"}";
        result = result.replaceAll( "\\\\", "\\\\" );
        if(callback == null ){
            return result ;
        }else{
            return "<script>"+ callback +"(" + result + ")</script>";
        }
    }

    //发表游记：携带游记id
    @PostMapping("/saveOrUpdate")
    @RequireLogin
    public JsonResult saveOrUpdate(Travel travel, @CurrentUser Userinfo user){
        travel.setAuthor(user);
        travel.setAuthorId(user.getId());
        long travelId = travelService.insert(travel);
        return JsonResult.success(travelId);
    }

    //游记详情
    @GetMapping("/detail")
    public JsonResult detail(Long id){
        Travel travel = travelService.getById(id);
        TravelContent content = travelContentService.getById(id);
        Userinfo user = userinfoService.getById(travel.getAuthorId());
        //设置显示headImgUrl
        travel.setAuthor(user);
        //设置显示content.content格式数据
        travel.setContent(content);
        return JsonResult.success(travel);
    }

    //游记评论存储
    @PostMapping("/commentAdd")
    @RequireLogin
    public JsonResult commentAdd(TravelComment comment,@CurrentUser Userinfo userinfo){
        BeanUtils.copyProperties(userinfo,comment);
        comment.setId(null);
        comment.setUserId(userinfo.getId());
        commentService.save(comment);
        return JsonResult.success();
    }

    //游记评论展示
    @GetMapping("/comments")
    public JsonResult comments(Long travelId){
        List<TravelComment> travelComments = commentService.queryByTravelId(travelId);
        return JsonResult.success(travelComments);
    }
}

