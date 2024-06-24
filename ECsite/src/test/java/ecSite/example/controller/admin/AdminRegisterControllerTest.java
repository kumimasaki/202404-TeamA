package ecSite.example.controller.admin;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ModelResultMatchers;

import ecSite.example.service.AdminService;
import jakarta.servlet.http.HttpSession;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminRegisterControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private HttpSession session;
	
	@MockBean
	private AdminService adminService;
	
	// サービスクラスを使ったデータ作成
	@BeforeEach
	public void prepareData() {
		// 登録できる場合　 "bob@gmail.com" true
		when(adminService.userCheck("bob@gmail.com")).thenReturn(true);
		// 登録できない場合　userEmail="admin@gmail.com",　　false
		when(adminService.userCheck("admin@test.com")).thenReturn(false);
		// 登録できる場合　"bob", "bob@gmail.com" "abc12345"  true
		when(adminService.createAdmin("Bob", "bob@gmail.com", "abcd12345")).thenReturn(true);
		// 登録できない場合　userEmail="admin@gmail.com", パスワードはどんな値でも良い　　false
		when(adminService.createAdmin(any(), eq("admin@test.com"), any())).thenReturn(false);
		
	}

	// 登録画面が正常に表示できるテスト
	@Test
	public void testGetRegisterPage_Succeed()throws Exception{
		RequestBuilder request = MockMvcRequestBuilders
				.get("/admin/register");
		
		mockMvc.perform(request)
		.andExpect(view().name("admin/admin_register.html"));
	}
	@Test
	public void testRegiste_NewAdminEmail_confirm()throws Exception{
		RequestBuilder Request = MockMvcRequestBuilders
				.post("/admin/confirm")
				.param("adminName", "Bob")
				.param("adminEmail", "bob@gmail.com")
				.param("adminPassword", "abcd12345");
		mockMvc.perform(Request)
		.andExpect(model().attribute("adminName", "Bob"))
		.andExpect(model().attribute("adminEmail", "bob@gmail.com"))
		.andExpect(view().name("admin/admin_register_confirm.html"));
	}
	

	// adminの登録が成功するかのテスト
	@Test
	public void testRegiste_NewAdminEmail_succeed()throws Exception{
		RequestBuilder Request = MockMvcRequestBuilders
				.post("/admin/register/process")
				.param("adminName", "Bob")
				.param("adminEmail", "bob@gmail.com")
				.param("adminPassword", "abcd12345");
		
		mockMvc.perform(Request)
		.andExpect(view().name("admin/admin_login.html"));
	}
	
	// ユーザーの登録が失敗するかのテスト
	@Test
	public void testRegister_ExistingAdminEmail_False()throws Exception{
		RequestBuilder request = MockMvcRequestBuilders
				.post("/admin/register/process")
				.param("adminName", "Admin")
				.param("adminEmail", "admin@test.com")
				.param("adminPassword", "1234abcd");
		mockMvc.perform(request)
		.andExpect(model().attribute("error", true))
		.andExpect(view().name("admin/admin_register.html"));
	}
}
