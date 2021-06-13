import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './booking-element.reducer';
import { IBookingElement } from 'app/shared/model/booking-element.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBookingElementProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const BookingElement = (props: IBookingElementProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { bookingElementList, match, loading } = props;
  return (
    <div>
      <h2 id="booking-element-heading" data-cy="BookingElementHeading">
        <Translate contentKey="secretplacespaApp.bookingElement.home.title">Booking Elements</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="secretplacespaApp.bookingElement.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="secretplacespaApp.bookingElement.home.createLabel">Create new Booking Element</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bookingElementList && bookingElementList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="secretplacespaApp.bookingElement.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.bookingElement.startDate">Start Date</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.bookingElement.endDate">End Date</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.bookingElement.elementPrice">Element Price</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.bookingElement.bookingId">Booking Id</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.bookingElement.housingTemplateId">Housing Template Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bookingElementList.map((bookingElement, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${bookingElement.id}`} color="link" size="sm">
                      {bookingElement.id}
                    </Button>
                  </td>
                  <td>
                    {bookingElement.startDate ? <TextFormat type="date" value={bookingElement.startDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {bookingElement.endDate ? <TextFormat type="date" value={bookingElement.endDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{bookingElement.elementPrice}</td>
                  <td>
                    {bookingElement.bookingId ? (
                      <Link to={`booking/${bookingElement.bookingId.id}`}>{bookingElement.bookingId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {bookingElement.housingTemplateId ? (
                      <Link to={`housing-template/${bookingElement.housingTemplateId.id}`}>{bookingElement.housingTemplateId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${bookingElement.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${bookingElement.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${bookingElement.id}/delete`}
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
              <Translate contentKey="secretplacespaApp.bookingElement.home.notFound">No Booking Elements found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ bookingElement }: IRootState) => ({
  bookingElementList: bookingElement.entities,
  loading: bookingElement.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BookingElement);
