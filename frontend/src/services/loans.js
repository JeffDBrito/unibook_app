import { api } from "./api";

export async function getLoans() {
	const response = await api("/loan");

	if (!response.ok) {
		throw new Error("Error loading loans");
	}

	return response.json();
}

export async function returnLoan(id) {
	const response = await api("/loan/" + id + "/return", {
		method: "POST"
	});

	if (!response.ok) {
		throw new Error("Error returning loan");
	}

	return response.json();
}