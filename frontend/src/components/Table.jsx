export default function Table({
	columns,
	data = [],
	loading = false,
	error = "",
	search = "",
	onSearchChange,
	searchPlaceholder = "Search...",
	page = 0,
	totalPages = 0,
	onPageChange,
}) {
	return (
		<div>
			{onSearchChange && (
				<div className="mb-3">
					<h5 htmlFor="search" className="form-label px-2">
						Search
					</h5>
					<input
						type="search"
						className="form-control ml-2"
						placeholder={searchPlaceholder}
						value={search}
						onChange={(event) => onSearchChange(event.target.value)}
					/>
				</div>
			)}

			<div className="table-responsive">
				<table
					style={{
						width: "100%",
						borderCollapse: "collapse",
						background: "#fff",
						borderRadius: "8px",
						overflow: "hidden",
					}}
				>
					<thead style={{ background: "#f3f4f6" }}>
						<tr>
							{columns.map((column) => (
								<th
									key={column.key}
									style={{
										textAlign: "left",
										padding: "12px",
										borderBottom: "1px solid #ddd",
									}}
									className={column.colSize ? `col-${column.colSize}` : ""}
								>
									{column.label}
								</th>
							))}
						</tr>
					</thead>

					<tbody>
						{loading && (
							<tr>
								<td
									colSpan={columns.length}
									className="text-center p-4"
								>
									Loading...
								</td>
							</tr>
						)}

						{!loading && error && (
							<tr>
								<td
									colSpan={columns.length}
									className="text-center text-danger p-4"
								>
									{error}
								</td>
							</tr>
						)}

						{!loading && !error && data.length === 0 && (
							<tr>
								<td
									colSpan={columns.length}
									className="text-center text-muted p-4"
								>
									No records found
								</td>
							</tr>
						)}

						{!loading &&
							!error &&
							data.map((row, rowIndex) => (
								<tr
									key={row.id}
									style={{
										background: rowIndex % 2 === 0 ? "#fff" : "#f9fafb",
									}}
									onMouseEnter={(event) => {
										event.currentTarget.style.background = "#e5e7eb";
									}}
									onMouseLeave={(event) => {
										event.currentTarget.style.background =
											rowIndex % 2 === 0 ? "#fff" : "#f9fafb";
									}}
								>
									{columns.map((column) => (
										<td
											key={column.key}
											style={{
												padding: "12px",
												borderBottom: "1px solid #eee",
											}}
											className={
												column.colSize ? `col-${column.colSize}` : ""
											}
										>
											{column.render
												? column.render(row)
												: row[column.accessor ?? column.key]}
										</td>
									))}
								</tr>
							))}
					</tbody>
				</table>
			</div>

			{!loading && !error && totalPages > 0 && (
				<div className="d-flex justify-content-between align-items-center mt-3">
					<button
						type="button"
						className="btn btn-outline-primary"
						disabled={page === 0}
						onClick={() => onPageChange(page - 1)}
					>
						Previous
					</button>

					<span>
						Page {page + 1} of {totalPages}
					</span>

					<button
						type="button"
						className="btn btn-outline-primary"
						disabled={page + 1 >= totalPages}
						onClick={() => onPageChange(page + 1)}
					>
						Next
					</button>
				</div>
			)}
		</div>
	);
}