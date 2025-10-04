import  ReactDOM  from "react-dom/client";
import React, { FunctionComponent, useState, useEffect } from 'react';
import { ilot} from './type';

const App : FunctionComponent = () => {
 // const [ilots, setilots] = useState<ilot[]>([]);
  const [ilots, setIlots] = useState<ilot[]>([]);
  const [newIlot, setNewIlot] = useState<ilot>({ id_il: "", nom: '', id_Un: "" });


  useEffect(() => {
    if (ilots) {
      fetch('http://localhost:8080/gI/ilots')

        .then(res=> res.json())
        .then(data => {
          setIlots(data);
        })
        .catch(error => {
           
            console.log(error)});
    }
    
 
  }, [ilots]);




  const handleDelete = (id: string) => {
    fetch(`http://localhost:8080/gI/del_ilot/${id}`, { method: "DELETE" })
      .then((res) => res.json())
      .then((data) => {
        // Mettre à jour la liste des ilots en supprimant l'ilot avec l'ID correspondant
        setIlots((prevIlots) => prevIlots.filter((ilot) => ilot.id_il !== id));
      })
      .catch((error) => {
        console.log(error);
      });
  };



  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const target = event.target;
    const value = target.value;
    const name = target.name;

    setNewIlot(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleAddIlot = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    fetch('http://localhost:8080/gI/ilots', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newIlot)
    })
      .then(res => res.json())
      .then(data => {
        setIlots(prevState => [...prevState, data]);
        setNewIlot({ id_il: "", nom: '', id_Un: "" });
      })
      .catch(error => {
        console.log(error);
      });
  };


  return (
    <div>
      <table>
        <thead>
          <tr>
            <th>id</th>
            <th>nom</th>
            <th>id_un</th>
            <th>Action</th>
           
          </tr>
        </thead>
        <tbody>
          {ilots.map((ilot) => {return(
            <tr key={ilot.id_il}>
              <td>{ilot.id_il}</td>
              <td>{ilot.nom}</td>
              <td>{ilot.id_Un}</td>
              <button onClick={() => handleDelete(ilot.id_il)}>Supprimer</button>

            </tr>)
})}
        </tbody>
      </table>
      <form onSubmit={handleAddIlot}>
        <label>
          CodeW:
          <input
            type="text"
            name="nom"
            value={newIlot.nom}
            onChange={handleInputChange}
          />
        </label>
        <label>
          Nom:
          <input
            type="text"
            name="id_Un"
            value={newIlot.id_Un}
            onChange={handleInputChange}
          />
        </label>
        <button type="submit">Ajouter</button>
      </form>
    </div>
  );
};

const rootElement = document.querySelector('#root');
if (rootElement) {
  const root = ReactDOM.createRoot(rootElement);
  root.render(<App />);
} else {
  console.error('Element with ID "root" not found');
}











