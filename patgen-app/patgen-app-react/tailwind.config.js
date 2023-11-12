/** @type {import('tailwindcss').Config} */
export default {
	content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
	theme: {
		extend: {
			colors: {
				// Default: 800
				"hnikt-darkblue": {
					DEFAULT: "#003283",
					50: "#B6D2FF",
					100: "#A2C5FF",
					200: "#79ACFF",
					300: "#5093FF",
					400: "#277AFF",
					500: "#0061FD",
					600: "#0051D5",
					700: "#0042AC",
					800: "#003283",
					900: "#001D4B",
					950: "#00122F",
				},
			},
		},
	},
	plugins: [],
};
