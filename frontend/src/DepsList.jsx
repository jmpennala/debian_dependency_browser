import { Link } from "react-router-dom";

const DepsList = ({ deps }) => (
  <div>
    <h1>Dependencies</h1>
    {deps?.map((dep) => (
      <div key={dep.package}>
        <Link to={dep.package}>{dep.package}</Link>
      </div>
    ))}
  </div>
);

export default DepsList;
