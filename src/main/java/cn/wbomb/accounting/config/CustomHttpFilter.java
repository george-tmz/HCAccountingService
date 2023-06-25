package cn.wbomb.accounting.config;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

@Slf4j
public class CustomHttpFilter extends PermissionsAuthorizationFilter {
    @Override
    protected boolean pathsMatch(String path, ServletRequest request) {
        String currentPath = getPathWithinApplication(request);
        log.info("Path in CustomHttpFilter: {}, current: {}", path, currentPath);
        val array = path.split("::");
        String url = array[0];
        boolean isHttpMethodMatched = true;
        if (array.length > 1) {
            val methodRequest = ((HttpServletRequest) request).getMethod().toUpperCase();
            val method = array[1];
            isHttpMethodMatched = method.equals(methodRequest);
        }
        return pathsMatch(url, currentPath) && isHttpMethodMatched;
    }
}
