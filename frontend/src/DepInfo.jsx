import { Link } from "react-router-dom";

const DepInfo = ({ dep }) => {
  return (
    <>
      <Link to="/">Back to list</Link>
      <h1>{dep.package}</h1>
      <div>
        <h2>Short description</h2>
        {dep.short_description}
      </div>
      <div>
        <h2>Full description</h2>
        {dep.description}
      </div>
      <div>
        <h2>Dependencies</h2>
        {dep.depends?.map((d) => (
          <div>
            <Link to={d}>{d}</Link>
          </div>
        ))}
      </div>
    </>
  );
};

export default DepInfo;
