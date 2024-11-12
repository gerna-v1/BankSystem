import React from 'react';

const FormContainer = ({ children, title, description }) => {
  return (
    <div className="flex flex-col w-full md:w-1/2 xl:w-2/5 2xl:w-2/5 3xl:w-1/3 mx-auto p-8 md:p-10 2xl:p-12 3xl:p-14 bg-white rounded-2xl shadow-xl">
      <h1 className="text-3xl font-bold text-gray-700 my-auto">{title}</h1>
      <div className="text-sm font-light text-gray-500 pb-8">{description}</div>
      {children}
    </div>
  );
};

export default FormContainer;