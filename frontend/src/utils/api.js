import { handleGlobalError } from './globalErrorHandler';

export const api = {
  async post(url = `${API_URL}/client/individual`, data) {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
      credentials: 'include'
    });

    const responseData = await response.json();

    if (!response.ok) {
      if (response.error.contains("Acesso negado")) {
        sessionStorage.clear()
      }

      throw handleGlobalError({ response: { status: response.status, data: responseData } });
    }

    return responseData;
  },

  async get(url) {
    const response = await fetch(url, { credentials: 'include'});

    if (response.status == 204)
      return


    const data = await response.json();

    if (!response.ok) {
      throw handleGlobalError({ response: { status: response.status, data } });
    }

    return data;
  },

  async put(url, data) {
    const response = await fetch(url, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
      credentials: 'include'
    });

    const responseData = await response.json();

    if (!response.ok) {
      throw handleGlobalError({ response: { status: response.status, data: responseData } });
    }

    return responseData;
  },

  async delete(url) {
    const response = await fetch(url, {
      method: 'DELETE',
      credentials: 'include'
    });

    if (!response.ok) {
      throw handleGlobalError({ response: { status: response.status, data } });
    }

    return true;
  }
}; 