# AccountKitSample
Test Error in AccessToken


Reproduce:

- Step 1: Login with Phone to get 1st Access Token.
- Step 2: Login with Email to get 2nd Access Token.
- Step 3: Try to check info of each token through command:

```
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET https://graph.accountkit.com/v2.10/me/?access_token=<TOKEN_HERE>
```

With account-kit-sdk *4.21.0* and below : Both tokens are verified normally

With account-kit-sdk *4.22.0* and above : 1st Access Token will return this value:


```
{
   "error": {
      "message": "Invalid OAuth 2.0 Access Token",
      "type": "EMApiException",
      "code": 190,
      "error_data": [
         
      ],
      "fbtrace_id": <fbtrace_id>
   }
}
```
