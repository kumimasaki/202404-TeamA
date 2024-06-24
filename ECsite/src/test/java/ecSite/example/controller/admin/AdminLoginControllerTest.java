package ecSite.example.controller.admin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.RequestBuilder;
import ecSite.example.model.entity.AdminEntity;
import ecSite.example.service.AdminService;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminLoginControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AdminService adminService;
	
	//サービスクラスを使ってデータの作成
	@BeforeEach
	public void prepareData() {
		AdminEntity admin = new AdminEntity("Admin", "admin@test.com", "1234abcd");
		// ログイン成功： userEmail "admin@test.com", password "1234abcd" ⇒true
		when(adminService.loginCheck(eq("admin@test.com"), eq("1234abcd"))).thenReturn(admin);
		// ログイン失敗： username="test@test.com", password="123" ⇒false
	    when(adminService.loginCheck(eq("test@test.com"), any())).thenReturn(null);
	}
	// ログイン画面を正しく取得するテスト
	@Test
	public void testGetLoginPage_Succeed()throws Exception{
		RequestBuilder request = MockMvcRequestBuilders
				.get("/admin/login");
		
		mockMvc.perform(request)
        .andExpect(view().name("admin/admin_login.html"));
	}
	// ログインが成功した場合のテスト
    // ログインが成功したら「blog_list.html」に遷移すること、入力された値が渡されていることのテスト
	@Test
	public void testLogin_CorrectInfo_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/admin/login/process")
				.param("adminEmail", "admin@test.com")
				.param("adminPassword", "1234abcd");
		
		mockMvc.perform(request)
        .andExpect(redirectedUrl("/admin/course/list"));
	}
	
	// ログインが失敗した場合のテスト
	// ログインが失敗したら画面が遷移しないこと、入力された値が渡されていることのテスト
	@Test
	public void testLogin_IncorrectInfo_False()throws Exception{
		RequestBuilder request = MockMvcRequestBuilders
				.post("/admin/login/process")
				.param("adminEmail", "test@test.com")
				.param("adminPassword", "1234abcd");
		mockMvc.perform(request)
		.andExpect(model().attribute("error", true))
		.andExpect(view().name("admin/admin_login.html"));
	}
}
