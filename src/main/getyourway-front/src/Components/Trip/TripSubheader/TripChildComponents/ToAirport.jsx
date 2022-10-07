function ToAirport() {
  return (
    <>
      <div class="form-outline">
        <div class="input-group">
          <span class="input-group-text" id="basic-addon1">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-geo-alt-fill"
              viewBox="0 0 16 16"
            >
              <path d="M8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10zm0-7a3 3 0 1 1 0-6 3 3 0 0 1 0 6z" />
            </svg>
          </span>
          <input
            type="text"
            id="formControlLg"
            class="form-control form-control-lg"
            placeholder="Destination Airport"
            aria-label="Destination Airport"
            aria-describedby="basic-addon1"
          />
        </div>
      </div>
    </>
  );
}

export default ToAirport;
