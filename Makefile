include .env
export

env:
	@echo "Updating component app files"

	@echo "Copying env to backend..."
	cp .env backend/.env

	@echo "Preparing frontend env..."
	cp .env frontend/.env

	@echo "Done ✅"

up-dev:
	@echo "Starting backend and frontend..."

	# Backend and frontend
	cd frontend && npm run app

	wait

ud:
	# make up-dev shortcut
	make up-dev

env-up-dev:
	make env
	make up-dev

eud:
	# env-up-dev shortcut
	make env-up-dev

kill:
	@echo "Killing backend and frontend processes..."
	pkill -f spring || true && pkill -f node || true

	@echo "Done ✅"