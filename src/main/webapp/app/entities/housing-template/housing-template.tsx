import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './housing-template.reducer';
import { IHousingTemplate } from 'app/shared/model/housing-template.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHousingTemplateProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const HousingTemplate = (props: IHousingTemplateProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { housingTemplateList, match, loading } = props;
  return (
    <div>
      <h2 id="housing-template-heading" data-cy="HousingTemplateHeading">
        <Translate contentKey="secretplacespaApp.housingTemplate.home.title">Housing Templates</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="secretplacespaApp.housingTemplate.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="secretplacespaApp.housingTemplate.home.createLabel">Create new Housing Template</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {housingTemplateList && housingTemplateList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="secretplacespaApp.housingTemplate.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.housingTemplate.housingType">Housing Type</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.housingTemplate.nbOfUnit">Nb Of Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.housingTemplate.nbMaxOfOccupants">Nb Max Of Occupants</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.housingTemplate.price">Price</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.housingTemplate.establishmentId">Establishment Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {housingTemplateList.map((housingTemplate, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${housingTemplate.id}`} color="link" size="sm">
                      {housingTemplate.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`secretplacespaApp.HousingType.${housingTemplate.housingType}`} />
                  </td>
                  <td>{housingTemplate.nbOfUnit}</td>
                  <td>{housingTemplate.nbMaxOfOccupants}</td>
                  <td>{housingTemplate.price}</td>
                  <td>
                    {housingTemplate.establishmentId ? (
                      <Link to={`establishment/${housingTemplate.establishmentId.id}`}>{housingTemplate.establishmentId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${housingTemplate.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${housingTemplate.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${housingTemplate.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="secretplacespaApp.housingTemplate.home.notFound">No Housing Templates found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ housingTemplate }: IRootState) => ({
  housingTemplateList: housingTemplate.entities,
  loading: housingTemplate.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HousingTemplate);
