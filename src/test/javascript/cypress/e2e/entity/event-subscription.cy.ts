import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('EventSubscription e2e test', () => {
  const eventSubscriptionPageUrl = '/event-subscription';
  const eventSubscriptionPageUrlPattern = new RegExp('/event-subscription(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const eventSubscriptionSample = { subscriptionDate: '2024-12-13', isActive: true };

  let eventSubscription;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/event-subscriptions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/event-subscriptions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/event-subscriptions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (eventSubscription) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/event-subscriptions/${eventSubscription.id}`,
      }).then(() => {
        eventSubscription = undefined;
      });
    }
  });

  it('EventSubscriptions menu should load EventSubscriptions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('event-subscription');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('EventSubscription').should('exist');
    cy.url().should('match', eventSubscriptionPageUrlPattern);
  });

  describe('EventSubscription page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(eventSubscriptionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create EventSubscription page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/event-subscription/new$'));
        cy.getEntityCreateUpdateHeading('EventSubscription');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventSubscriptionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/event-subscriptions',
          body: eventSubscriptionSample,
        }).then(({ body }) => {
          eventSubscription = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/event-subscriptions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/event-subscriptions?page=0&size=20>; rel="last",<http://localhost/api/event-subscriptions?page=0&size=20>; rel="first"',
              },
              body: [eventSubscription],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(eventSubscriptionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details EventSubscription page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('eventSubscription');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventSubscriptionPageUrlPattern);
      });

      it('edit button click should load edit EventSubscription page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EventSubscription');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventSubscriptionPageUrlPattern);
      });

      it('edit button click should load edit EventSubscription page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EventSubscription');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventSubscriptionPageUrlPattern);
      });

      it('last delete button click should delete instance of EventSubscription', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('eventSubscription').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', eventSubscriptionPageUrlPattern);

        eventSubscription = undefined;
      });
    });
  });

  describe('new EventSubscription page', () => {
    beforeEach(() => {
      cy.visit(`${eventSubscriptionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('EventSubscription');
    });

    it('should create an instance of EventSubscription', () => {
      cy.get(`[data-cy="subscriptionDate"]`).type('2024-12-13');
      cy.get(`[data-cy="subscriptionDate"]`).blur();
      cy.get(`[data-cy="subscriptionDate"]`).should('have.value', '2024-12-13');

      cy.get(`[data-cy="isActive"]`).should('not.be.checked');
      cy.get(`[data-cy="isActive"]`).click();
      cy.get(`[data-cy="isActive"]`).should('be.checked');

      cy.get(`[data-cy="anonymousEmail"]`).type('commettre longtemps');
      cy.get(`[data-cy="anonymousEmail"]`).should('have.value', 'commettre longtemps');

      cy.get(`[data-cy="anonymousName"]`).type('fourbe personnel professionnel trop');
      cy.get(`[data-cy="anonymousName"]`).should('have.value', 'fourbe personnel professionnel trop');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        eventSubscription = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', eventSubscriptionPageUrlPattern);
    });
  });
});
