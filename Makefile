env:
	@echo "Copying env to backend..."
	cp .env backend/.env

	@echo "Preparing frontend env..."
	cp .env frontend/.env

	@echo "Done ✅"

dev:
	@echo "Starting backend and frontend..."

	# Backend and frontend
	cd frontend && npm run app

	wait

kill:
	@echo "Killing backend and frontend processes..."
	pkill -f spring || true && pkill -f node || true

	@echo "Done ✅"