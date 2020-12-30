/**
    File: CompleteRestrictionSecurityRule
    Description: This is a template/skeleton class which shows how to 
    define a basic security rule along with the methods that need to be 
    overridden for this purpose. (Chapter 7 - Neo4j High Performance)
    Specifics: This code will block all URIs that try to communicate with this server.
**/
public class CompleteRestrictionSecurityRule implements SecurityRule
{

    public static final String MYREALM = "MyApplication";

    @Override
    public boolean isAuthorized( HttpServletRequest request )
    {
        return false; // Forces failure always
        // Logic for authorization coded here
    }

    @Override
    public String forUriPath()
    {
        return "/*"; //For any incoming URI path
    }

    @Override
    public String wwwAuthenticateHeader()
    {
        return SecurityFilter.basicAuthenticationResponse(MYREALM);
    }
}
