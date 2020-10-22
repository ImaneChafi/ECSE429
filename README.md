# ECSE429
ECSE 429 project repo!!

Example of test code with Java HTTP Client: 

<@Test
public void givenUserDoesNotExists_whenUserInfoIsRetrieved_then404IsReceived()
  throws ClientProtocolException, IOException {
 
    // Given
    String name = RandomStringUtils.randomAlphabetic( 8 );
    HttpUriRequest request = new HttpGet( "https://api.github.com/users/" + name );
 
    // When
    HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
 
    // Then
    assertThat(
      httpResponse.getStatusLine().getStatusCode(),
      equalTo(HttpStatus.SC_NOT_FOUND));
}>

