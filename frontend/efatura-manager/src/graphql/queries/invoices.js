import gql from 'graphql-tag';

export const allInvoicesQuery = gql`
     query {
         invoiceCount
         findAllInvoices {
             uuid
             invoiceNumber
             direction
             issueDate
             documentCurrencyCode
             lineExtensionAmount {
                 amount
                 currencyCode
             }
             accountingSupplierParty {
                 partyName
             }
             accountingCustomerParty {
                 partyName
             }
             processed
             integrationStatus
         }
    }
`;

export const invoiceCount = gql`
    query {
        invoiceCount
    }
`;

export const pageInvoicesQuery = gql`
    query ($pageSize: Int!, $pageNumber: Int!, $sortBy: String!, $sortOrder: SortOrder!) {
        findInvoices(pageSize: $pageSize, pageNumber: $pageNumber, sortBy: $sortBy, sortOrder: $sortOrder) {
            uuid
            invoiceNumber
            direction
            issueDate
            documentCurrencyCode
            lineExtensionAmount {
                amount
                currencyCode
            }
            accountingSupplierParty {
                partyName
            }
            accountingCustomerParty {
                partyName
            }
            processed
            integrationStatus
        }
    }
`;

export const searchInvoicesQuery = gql`
    query ($direction: String!, $filter: String) {
        searchInvoices(direction: $direction, filter: $filter) {
            uuid
            invoiceNumber
            direction
            issueDate
            documentCurrencyCode
            lineExtensionAmount {
                amount
                currencyCode
            }
            accountingSupplierParty {
                partyName
            }
            accountingCustomerParty {
                partyName
            }
            processed
            integrationStatus
        }
    }
`;