package cn.wbomb.accounting.config;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;

@Slf4j
public class CustomPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {
    @Override
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        }
        String currentPath = getPathWithinApplication(request);
        log.info("currentPath in CustomPathMatchingFilterChainResolver: {}", currentPath);
        return filterChainManager.getChainNames().stream()
            .filter(chain -> isHttpRequestMatched(chain, currentPath, request))
            .findAny()
            .map(chain -> filterChainManager.proxy(originalChain, chain))
            .orElse(null);
    }

    /**
     * Compare whether the http request matched.
     * 1.Compare request url
     * 2.If it has Http method mark, compare http method.
     *
     * @param chain       the specific chain
     * @param currentPath the current path.
     * @param request     the http request.
     * @return the flag indicate whether http request matched.
     */
    private boolean isHttpRequestMatched(String chain, String currentPath, ServletRequest request) {
        val array = chain.split("::");
        String url = array[0];
        boolean isHttpMethodMatched = true;
        if (array.length > 1) {
            val methodRequest = ((HttpServletRequest) request).getMethod().toUpperCase();
            val method = array[1];
            isHttpMethodMatched = method.equals(methodRequest);
        }
        return pathMatches(url, currentPath) && isHttpMethodMatched;
    }

}
