export default function Table({ columns, data }) {
	return (
		<table
			style={{
				width: "100%",
				borderCollapse: "collapse",
				background: "#fff",
				borderRadius: "8px",
				overflow: "hidden"
			}}
		>
			<thead style={{ background: "#f3f4f6" }}>
			<tr>
				{columns.map((col, colIndex) => (
  				<th key={colIndex}
						style={{
							textAlign: "left",
							padding: "12px",
							borderBottom: "1px solid #ddd"
						}}
					>
						{col.label}
					</th>
				))}
			</tr>
		</thead>

			<tbody>
				{data.map((row, index) => (
					<tr
						key={row.id}
						style={{
							background: index % 2 === 0 ? "#fff" : "#f9fafb"
						}}
						onMouseEnter={(e) =>
							(e.currentTarget.style.background = "#e5e7eb")
						}
						onMouseLeave={(e) =>
							(e.currentTarget.style.background =
								index % 2 === 0 ? "#fff" : "#f9fafb")
						}
					>
						{columns.map((col, colIndex) => (
  						<td key={colIndex}
								style={{
									padding: "12px",
									borderBottom: "1px solid #eee"
								}}
							>
								{col.render ? col.render(row) : row[col.accessor]}
							</td>
						))}
					</tr>
				))}
			</tbody>
		</table>
	);
}