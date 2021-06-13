import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './booking.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBookingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BookingDetail = (props: IBookingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bookingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bookingDetailsHeading">
          <Translate contentKey="secretplacespaApp.booking.detail.title">Booking</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="secretplacespaApp.booking.email">Email</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.email}</dd>
          <dt>
            <span id="totalPrice">
              <Translate contentKey="secretplacespaApp.booking.totalPrice">Total Price</Translate>
            </span>
            <UncontrolledTooltip target="totalPrice">
              <Translate contentKey="secretplacespaApp.booking.help.totalPrice" />
            </UncontrolledTooltip>
          </dt>
          <dd>{bookingEntity.totalPrice}</dd>
          <dt>
            <span id="comment">
              <Translate contentKey="secretplacespaApp.booking.comment">Comment</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.comment}</dd>
          <dt>
            <span id="validate">
              <Translate contentKey="secretplacespaApp.booking.validate">Validate</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.validate ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="secretplacespaApp.booking.jhiUserId">Jhi User Id</Translate>
          </dt>
          <dd>{bookingEntity.jhiUserId ? bookingEntity.jhiUserId.id : ''}</dd>
          <dt>
            <Translate contentKey="secretplacespaApp.booking.establishmentId">Establishment Id</Translate>
          </dt>
          <dd>{bookingEntity.establishmentId ? bookingEntity.establishmentId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/booking" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/booking/${bookingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ booking }: IRootState) => ({
  bookingEntity: booking.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BookingDetail);
