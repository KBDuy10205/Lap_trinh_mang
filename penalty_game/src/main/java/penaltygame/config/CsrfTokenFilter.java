package penaltygame.config;

import java.io.IOException;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Yêu cầu token riêng của session cho các lệnh HTTP làm thay đổi dữ liệu.
 */
@Component
public class CsrfTokenFilter extends OncePerRequestFilter {

	public static final String SESSION_TOKEN_NAME = "csrfToken";
	public static final String HEADER_TOKEN_NAME = "X-CSRF-TOKEN";

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String method = request.getMethod();
		String path = request.getServletPath();

		return HttpMethod.GET.matches(method) || HttpMethod.HEAD.matches(method)
				|| HttpMethod.OPTIONS.matches(method) || !path.startsWith("/api/");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String expectedToken = session == null ? null : (String) session.getAttribute(SESSION_TOKEN_NAME);
		String receivedToken = request.getHeader(HEADER_TOKEN_NAME);

		if (expectedToken == null || !expectedToken.equals(receivedToken)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token không hợp lệ.");
			return;
		}

		filterChain.doFilter(request, response);
	}
}
