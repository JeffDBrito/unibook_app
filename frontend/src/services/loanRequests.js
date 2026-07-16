import { api } from "./api";

async function parseError(response, fallback) {
  const body = await response.json().catch(() => null);

  throw body || {
    general: fallback,
  };
}

export async function getLoanRequests({
  page = 0,
  size = 10,
  search = "",
  status = "PENDING",
} = {}) {
  const params = new URLSearchParams({
    page: String(page),
    size: String(size),
    status,
  });

  if (search.trim()) {
    params.set("search", search.trim());
  }

  const response = await api(
    `/loan-requests?${params.toString()}`
  );

  if (!response.ok) {
    return parseError(
      response,
      "Error loading loan requests"
    );
  }

  return response.json();
}

export async function approveLoanRequest(id) {
  const response = await api(
    `/loan-requests/${id}/approve`,
    {
      method: "POST",
    }
  );

  if (!response.ok) {
    return parseError(
      response,
      "Error approving loan request"
    );
  }

  return response.json();
}

export async function rejectLoanRequest(id) {
  const response = await api(
    `/loan-requests/${id}/reject`,
    {
      method: "POST",
    }
  );

  if (!response.ok) {
    return parseError(
      response,
      "Error rejecting loan request"
    );
  }

  return response.json();
}

export async function requestLoan(bookId) {
    const response = await api("/loan-requests", {
        method: "POST",
        body: JSON.stringify({
            bookId
        })
    });

    if (!response.ok) {
        throw await response.json();
    }

    return response.json();
}