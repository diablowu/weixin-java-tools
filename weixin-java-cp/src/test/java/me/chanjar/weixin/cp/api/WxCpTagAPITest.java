//package me.chanjar.weixin.cp.api;
//
//import java.util.List;
//
//import me.chanjar.weixin.cp.bean.WxCpTag;
//
//import org.junit.Assert;
//
//public class WxCpTagAPITest {
//
//  protected WxCpServiceImpl wxService;
//
//  protected WxCpConfigStorage configStorage;
//
//  protected String tagId;
//
//  public void testTagCreate() throws Exception {
//    tagId = wxService.tagCreate("测试标签4");
//    System.out.println(tagId);
//  }
//
//  public void testTagUpdate() throws Exception {
//    wxService.tagUpdate(tagId, "测试标签-改名");
//  }
//
//  public void testTagGet() throws Exception {
//    List<WxCpTag> tags = wxService.tagGet();
//    Assert.assertNotEquals(tags.size(), 0);
//  }
//
////  public void testTagAddUsers() throws Exception {
////    List<String> userIds = new ArrayList<String>();
////    userIds.add(((ApiTestModule.WxXmlCpInMemoryConfigStorage)configStorage).getUserId());
////    wxService.tagAddUsers(tagId, userIds);
////  }
////
////  public void testTagGetUsers() throws Exception {
////    List<WxCpUser> users = wxService.tagGetUsers(tagId);
////    Assert.assertNotEquals(users.size(), 0);
////  }
////
////  public void testTagRemoveUsers() throws Exception {
////    List<String> userIds = new ArrayList<String>();
////    userIds.add(((ApiTestModule.WxXmlCpInMemoryConfigStorage)configStorage).getUserId());
////    wxService.tagRemoveUsers(tagId, userIds);
////  }
//  public void testTagDelete() throws Exception {
//    wxService.tagDelete(tagId);
//  }
//
//}
