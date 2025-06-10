/** @type {import('tailwindcss').Config} */
module.exports = {
  // La parte más importante de la configuración:
  // Le decimos a Tailwind que 'mire' dentro de la carpeta 'templates'
  // y analice todos los archivos .html en busca de clases de utilidad.
  content: [
    './proj/src/main/resources/templates/**/*.html'
  ],

  theme: {
    extend: {
      colors: {
        'fondo': '#ECF2F0', // Fondo de bienvenida
        'surface': '#F9F9F9',    // Fondo de la página/tarjetas
        'warning': '#FEE7DF',    // Color para advertencias
        'text-main': '#000000',  // Color de texto principal
        'importante': '#6f66eb' 
          
        
      },
      fontFamily: {
        sans: ['Noto Sans', 'sans-serif'],
      }
    },
  },

  plugins: [],
}
