const BASE_URL = "http://localhost:8080";

export async function api(path, options = {}) {
  const token = localStorage.getItem("token");

  return fetch(`${BASE_URL}${path}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(token && { Authorization: `Bearer ${token}` }),
      ...options.headers
    }
  });
}