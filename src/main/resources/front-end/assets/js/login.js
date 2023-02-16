window.onload = () =>
   document.querySelector('form').addEventListener('submit', (e) => {
      e.preventDefault();
      const submitFormElement = document.querySelector('form');
      console.log('submitFormElement', submitFormElement);
      const formData = new FormData(submitFormElement);
      const formProps = Object.fromEntries(formData);
      console.log('formData', formData);
      console.log('formProps', formProps);
      sendAnswer(JSON.stringify(formProps));
      console.log(Object.values(formProps));
   });

async function sendAnswer(data) {
   let url = '';
   let urlDev = 'localhost:3000/login';
   const response = await fetch(urlDev, {
      method: 'POST',
      headers: {
         'content-type': 'text/plain',
         'access-control-allow-origin': '*',
         vary: 'Accept-Encoding',
      },
      body: data,
   });
   try {
      console.log('sucesso!', response);
      // window.location.href = 'sucess.index';
      return response;
   } catch (error) {
      console.log('erro na resposta:', error);
   }
}
