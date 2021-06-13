import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './housing-closure.reducer';
import { IHousingClosure } from 'app/shared/model/housing-closure.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHousingClosureProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const HousingClosure = (props: IHousingClosureProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { housingClosureList, match, loading } = props;
  return (
    <div>
      <h2 id="housing-closure-heading" data-cy="HousingClosureHeading">
        <Translate contentKey="secretplacespaApp.housingClosure.home.title">Housing Closures</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="secretplacespaApp.housingClosure.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="secretplacespaApp.housingClosure.home.createLabel">Create new Housing Closure</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {housingClosureList && housingClosureList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="secretplacespaApp.housingClosure.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.housingClosure.startDate">Start Date</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.housingClosure.endDate">End Date</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.housingClosure.cause">Cause</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.housingClosure.establishmentId">Establishment Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {housingClosureList.map((housingClosure, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${housingClosure.id}`} color="link" size="sm">
                      {housingClosure.id}
                    </Button>
                  </td>
                  <td>
                    {housingClosure.startDate ? <TextFormat type="date" value={housingClosure.startDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {housingClosure.endDate ? <TextFormat type="date" value={housingClosure.endDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{housingClosure.cause}</td>
                  <td>
                    {housingClosure.establishmentId ? (
                      <Link to={`housing-template/${housingClosure.establishmentId.id}`}>{housingClosure.establishmentId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${housingClosure.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${housingClosure.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${housingClosure.id}/delete`}
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
              <Translate contentKey="secretplacespaApp.housingClosure.home.notFound">No Housing Closures found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ housingClosure }: IRootState) => ({
  housingClosureList: housingClosure.entities,
  loading: housingClosure.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HousingClosure);
