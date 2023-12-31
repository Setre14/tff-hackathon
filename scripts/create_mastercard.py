import os
import pandas as pd

def create_mastercard_csv(path, out_path):
    out_path = os.path.join(out_path, "mastercard")
    os.makedirs(out_path)

    # Define the file path
    file_path = os.path.join(path, 'GeoInsights_Synthetic_Output.csv')

    # Define the prefixes to filter by
    prefixes = ['12023003232', '12023003230']

    # Read the CSV file
    df = pd.read_csv(file_path)

    # Convert the txn_date column to datetime for proper sorting
    df['txn_date'] = pd.to_datetime(df['txn_date'])

    # Rename txn_date to @timestamp
    df.rename(columns={'txn_date': '@timestamp'}, inplace = True)
    print(df)

    # Filter the dataframe based on quad_id prefixes
    filtered_df = df[df['quad_id'].astype(str).apply(lambda x: any(x.startswith(prefix) for prefix in prefixes))]

    # Get unique industries
    industries = filtered_df['industry'].unique()


    output_path = os.path.join(out_path, "all-transactions.csv")
    filtered_df.to_csv(output_path, index=False)


    # Loop through each industry and save a separate CSV
    for industry in industries:
        industry_df = filtered_df[filtered_df['industry'] == industry]

        # Select only numeric columns for aggregation
        numeric_cols = industry_df.select_dtypes(include='number').columns

        # Group by date and calculate the mean for numeric columns only
        grouped_df = industry_df.groupby('@timestamp')[numeric_cols].mean().reset_index()
        
        # # Drop the industry and quad_id columns
        # grouped_df.drop(['industry', 'quad_id'], axis=1, inplace=True, errors='ignore')
        grouped_df['industry'] = [industry] * len(grouped_df)

        # Convert industry name to lowercase and replace spaces and slashes with underscores
        file_name = 'industry-' + industry.lower().replace(' ', '_').replace('/', '_') + '.csv'
        output_path = os.path.join(out_path, file_name)

        # Save the grouped and ordered data to a new CSV
        grouped_df.to_csv(output_path, index=False)
