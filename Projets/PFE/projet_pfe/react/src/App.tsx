import React, { FunctionComponent, useState, useEffect } from 'react';
import { Sec } from './type';

const App : FunctionComponent = () => {
  const [secteurs, setSecteurs] = useState([]);

  useEffect(() => {
    if(!secteurs){
    fetch('http://localhost:3000/gI/secteurs_sauvegardes')
      .then(res=> res.json())
      .then(data => setSecteurs(data))
      .catch(error => console.log(error));
      console.log(secteurs)
    }
  }, [secteurs]);

  return (
    <div>
      <table>
        <thead>
          <tr>
            <th>id</th>
            <th>codeW</th>
            <th>nom</th>
            <th>classement</th>
          </tr>
        </thead>
        <tbody>
          {secteurs.map((secteur:Sec) => {return(
            <tr key={secteur.id_sec}>
              <td>{secteur.id_sec}</td>
              <td>{secteur.codeW}</td>
              <td>{secteur.nom}</td>
              <td>{secteur.classement}</td>
            </tr>)
})}
        </tbody>
      </table>
    </div>
  );
};



export default App
