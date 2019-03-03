package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.domain.User;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.PaymentUtil;
import com.itheima.utils.UUIDUtils;
import com.sun.org.apache.xpath.internal.operations.Or;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
 
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//0.�ж��û��Ƿ��¼
		User user =(User)request.getSession().getAttribute("user");
		if(user==null) {
			request.setAttribute("msg","please regist");
			return "/jsp/msg.jsp";
		}
		
		// 1.��װ����
		Order order = new Order();
		 //1.1����id
		order.setOid(UUIDUtils.getId());
		
		//1.2����ʱ��
		order.setOrdertime(new Date());
		
		//1.3�ܽ��
		Cart cart=(Cart) request.getSession().getAttribute("cart");
		
		order.setTotal(cart.getTotal());
		//1.4���������ж�����  
		/**
		 *  �Ȼ�ȡcart��items
		 *  ����items��װ��orderItem
		 *  ��orderItem���ӵ�list(items)��
		 */
		for(CartItem cartItem :cart.getItems()) {
			OrderItem oItem = new OrderItem();
			
			oItem.setItemid(UUIDUtils.getId());
			oItem.setCount(cartItem.getCount());
			oItem.setProduct(cartItem.getProduct());
			oItem.setOrder(order);
			
			order.getItems().add(oItem);
		}
		//1.5 �����û�   
		order.setUser(user);
		//2.����service ���Ӷ���  
		OrderService os= (OrderService)BeanFactory.getBean("OrderService");
		os.add(order);
		// 3.��order����request���У�����ת��
   		request.setAttribute("bean", order);
		//4.��չ��ﳵ
   		request.getSession().removeAttribute("cart");
   		
		return "/jsp/order_info.jsp";
	}
	//��ҳ��ѯ�ҵĶ���
	public String findAllPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 //1.��ȡ��ǰҳ
		int currPage=Integer.parseInt(request.getParameter("currPage"));
		  int pageSize=3;
		  //2.��ȡ�û�
		  User user = (User)request.getSession().getAttribute("user");
		  if(user==null) {
			  request.setAttribute("msg", "�㻹û�е�¼,���¼!");
			  return  "/jsp/msg.jsp";
		  }
		  
		  //3.����service  ��ҳ��ѯ  ������currPage pagesize user  ����ֵPageBean
		  OrderService os = (OrderService)BeanFactory.getBean("OrderService");
		  PageBean<Order> bean=os.findAllPage(currPage,pageSize,user);
		  
			//4.��pageBean����request����
		  request.setAttribute("pb", bean);
			return "/jsp/order_list.jsp";
		}
	
	
	public String getById(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String oid =(String) request.getParameter("oid");
		OrderService os = (OrderService)BeanFactory.getBean("OrderService");
		
		Order order = os.getById(oid);
		request.setAttribute("bean", order);
		
		return "/jsp/order_info.jsp";
	}
	
	public String pay(HttpServletRequest request,HttpServletResponse respone) throws Exception{
		//���ܲ���
		String address=request.getParameter("address");
		String name=request.getParameter("name");
		String telephone=request.getParameter("telephone");
		String oid=request.getParameter("oid");
		
		
		//ͨ��id��ȡorder
		OrderService s=(OrderService) BeanFactory.getBean("OrderService");
		Order order = s.getById(oid);
		
		order.setAddress(address);
		order.setName(name);
		order.setTelephone(telephone);
		
		//����order
		s.update(order);
		

		// ��֯����֧����˾��Ҫ��Щ����
		String pd_FrpId = request.getParameter("pd_FrpId");
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
		String p2_Order = oid;
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// ֧���ɹ��ص���ַ ---- ������֧����˾����ʡ��û�����
		// ������֧�����Է�����ַ
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// ����hmac ��Ҫ��Կ
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);
	
		
		//���͸�������
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);
		
		respone.sendRedirect(sb.toString());
		
		return null;
	}
	/**
	 * ֧���ɹ�֮��Ļص�
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String callback(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// ����У�� --- �ж��ǲ���֧����˾֪ͨ��
		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");

		// �Լ����������ݽ��м��� --- �Ƚ�֧����˾������hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// ��Ӧ������Ч
			if (r9_BType.equals("1")) {
				// ������ض���
				System.out.println("111");
				request.setAttribute("msg", "���Ķ�����Ϊ:"+r6_Order+",���Ϊ:"+r3_Amt+"�Ѿ�֧���ɹ�,�ȴ�����~~");
				
			} else if (r9_BType.equals("2")) {
				// ��������Ե� --- ֧����˾֪ͨ��
				System.out.println("����ɹ���222");
				// �޸Ķ���״̬ Ϊ�Ѹ���
				// �ظ�֧����˾
				response.getWriter().print("success");
			}
			
			//�޸Ķ���״̬
			OrderService s=(OrderService) BeanFactory.getBean("OrderService");
			Order order = s.getById(r6_Order);
			
			//�޸Ķ���״̬Ϊ ��֧��
			order.setState(1);
			
			s.update(order);
			
		} else {
			// ������Ч
			System.out.println("���ݱ��۸ģ�");
		}
		
		
		return "/jsp/msg.jsp";
		
	}
	
	
	
}