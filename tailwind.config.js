/** @type {import('tailwindcss').Config} */
module.exports = {
  
  content: [
    './proj/src/main/resources/templates/**/*.html'
  ],

  theme: {
    extend: {
      colors: {
        'fondo': '#ECF2F0', // Fondo de la pagina
        'surface': '#F9F9F9',    // Fondo de la página/tarjetas
        'warning': '#FEE7DF',    // Color para advertencias
        'text-main': '#000000',  // Color de texto principal
        'importante': '#6f66eb' //para botones 
          
        
      },
      fontFamily: {
        sans: ['Noto Sans', 'sans-serif'],
      }
    },
  },

  plugins: [],
}
