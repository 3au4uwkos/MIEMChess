import React from "react";
import "./MPLeaders.css";

const MPLeaders = () => {
    return (
        <div className="MPLeaders">
            <h2>Таблица <div className="highlighted">лидеров</div> </h2>
            <div className="MPLeadersTable">
                <div className="MPLeadersTableColumn">
                    <p>1. nickname 100</p>
                    <p>2. nickname 100</p>
                    <p>3. nickname 100</p>
                    <p>4. nickname 100</p>
                    <p>5. nickname 100</p>
                </div>
                <div className="MPLeadersTableColumn">
                    <p>6. nickname 100</p>
                    <p>7. nickname 100</p>
                    <p>8. nickname 100</p>
                    <p>9. nickname 100</p>
                    <p>10. nickname 100</p>
                </div>
            </div>
        </div>
    );
};

export default MPLeaders;