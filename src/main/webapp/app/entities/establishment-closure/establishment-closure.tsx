import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './establishment-closure.reducer';
import { IEstablishmentClosure } from 'app/shared/model/establishment-closure.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEstablishmentClosureProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const EstablishmentClosure = (props: IEstablishmentClosureProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { establishmentClosureList, match, loading } = props;
  return (
    <div>
      <h2 id="establishment-closure-heading" data-cy="EstablishmentClosureHeading">
        <Translate contentKey="secretplacespaApp.establishmentClosure.home.title">Establishment Closures</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="secretplacespaApp.establishmentClosure.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="secretplacespaApp.establishmentClosure.home.createLabel">Create new Establishment Closure</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {establishmentClosureList && establishmentClosureList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="secretplacespaApp.establishmentClosure.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.establishmentClosure.startDate">Start Date</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.establishmentClosure.endDate">End Date</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.establishmentClosure.cause">Cause</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.establishmentClosure.establishmentId">Establishment Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {establishmentClosureList.map((establishmentClosure, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${establishmentClosure.id}`} color="link" size="sm">
                      {establishmentClosure.id}
                    </Button>
                  </td>
                  <td>
                    {establishmentClosure.startDate ? (
                      <TextFormat type="date" value={establishmentClosure.startDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {establishmentClosure.endDate ? (
                      <TextFormat type="date" value={establishmentClosure.endDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{establishmentClosure.cause}</td>
                  <td>
                    {establishmentClosure.establishmentId ? (
                      <Link to={`establishment/${establishmentClosure.establishmentId.id}`}>{establishmentClosure.establishmentId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${establishmentClosure.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${establishmentClosure.id}/edit`}
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
                        to={`${match.url}/${establishmentClosure.id}/delete`}
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
              <Translate contentKey="secretplacespaApp.establishmentClosure.home.notFound">No Establishment Closures found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ establishmentClosure }: IRootState) => ({
  establishmentClosureList: establishmentClosure.entities,
  loading: establishmentClosure.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EstablishmentClosure);
