"use strict";

const serverApi = {
  async callUrl({
    data,
    url,
    method = 'GET'
  }) {
    debug.log(`[Request] URL: ${url}`, {
      data,
      method
    });
    let body = null;
    if (data) {
      body = typeof data === 'string' ? data : JSON.stringify(data);
    }
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Cache-Control', 'no-cache');
    try {
      const response = await fetch(url, {
        method,
        headers,
        body
      });
      if (response.status === 200) {
        const responseData = await response.json();
        debug.log(`[Response] URL: ${url}`, responseData);
        return {
          data: responseData
        };
      }
      debug.error(`Failed calling ${url}, status: ${response.status}, text: ${response.statusText}`);
      return {
        error: new Error(`Failed calling ${url}, status: ${response.status}, text: ${response.statusText}`)
      };
    } catch (e) {
      debug.error('Error in callUrl', e);
      return {
        error: e
      };
    }
  }
};