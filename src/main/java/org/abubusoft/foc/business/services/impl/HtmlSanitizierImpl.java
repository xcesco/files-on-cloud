package org.abubusoft.foc.business.services.impl;

import org.abubusoft.foc.business.services.HtmlSanitizier;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Component;

@Component
public class HtmlSanitizierImpl implements HtmlSanitizier{
	PolicyFactory policy;
	
	public HtmlSanitizierImpl() {
		policy = new HtmlPolicyBuilder()
			    .allowElements("a")
			    .allowUrlProtocols("https")
			    .allowAttributes("href").onElements("a")
			    .requireRelNofollowOnLinks()
			    .toFactory();
	}
	
	@Override
	public String sanitize(String untrustedHTML) {
		String safeHTML = policy.sanitize(untrustedHTML);
		
		return safeHTML;
	}
	
	
}
