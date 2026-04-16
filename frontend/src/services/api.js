import { ENV } from "../config/env";

const BASE_URL = ENV.API_URL;

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